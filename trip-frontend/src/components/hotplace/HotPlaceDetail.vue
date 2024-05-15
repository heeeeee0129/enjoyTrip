<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getArticle, deleteArticle } from "@/api/hotplace.js";
import { useUserStore } from "@/stores/index";

const userStore = useUserStore();

const route = useRoute();
const router = useRouter();

const { hotNo } = route.params;

const hotplace = ref({});
const ImgPath = ref("");

onMounted(() => {
  detailArticle();
});

function getImageUrl(folder, name) {
  return new URL(`/src/assets/upload/${folder}/${name}`, import.meta.url);
}

const detailArticle = async () => {
  const success = (response) => {
    hotplace.value = response.data;
    ImgPath.value = getImageUrl(
      hotplace.value.fileInfo.saveFolder,
      hotplace.value.fileInfo.saveFile
    );
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await getArticle(hotNo, success, fail);
};

function moveList() {
  router.push({ name: "HotPlaceList" });
}

function moveModify() {
  router.push({ name: "HotPlaceModify", params: { hotNo } });
}

const onDeleteArticle = async () => {
  const success = () => {
    alert("글이 삭제되었습니다");
    moveList();
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await deleteArticle(hotNo, success, fail);
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="card my-3 shadow-sm">
          <div class="card-body">
            <h2 class="card-title py-3 text-center">핫플레이스 상세보기</h2>
          </div>
          <div class="container mt-5">
            <div class="row">
              <!-- 왼쪽 반은 지도 -->
              <div class="col-md-6 mb-4">
                <!-- 여기에 지도를 표시할 컴포넌트를 넣으세요 -->
              </div>

              <!-- 오른쪽 반은 글작성 또는 글수정 폼 -->
              <div class="col-md-6 mb-4">
                <p class="text-end">조회수: {{ hotplace.hit }}</p>
                <form class="p-4 rounded-lg shadow bg-light">
                  <div class="mb-3">
                    <label for="userName" class="form-label">작성자:</label>
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="hotplace.userName"
                      readonly
                    />
                  </div>
                  <div class="mb-3">
                    <label for="placeName" class="form-label">장소 이름:</label>
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="hotplace.placeName"
                      readonly
                    />
                  </div>
                  <div class="mb-3">
                    <label for="visitedDate" class="form-label">다녀온 날짜:</label>
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="hotplace.registerTime"
                      readonly
                    />
                  </div>
                  <div class="mb-3">
                    <label for="graveType" class="form-label">장소 유형:</label>
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="hotplace.category"
                      readonly
                    />
                  </div>
                  <div class="mb-3">
                    <label for="introduction" class="form-label">핫플레이스 소개:</label>
                    <textarea
                      class="form-control"
                      :placeholder="hotplace.content"
                      rows="5"
                      readonly
                    ></textarea>
                  </div>
                  <div class="text-center">
                    <button
                      type="button"
                      class="btn btn-outline-primary rounded-pill px-4"
                      @click="moveModify"
                      v-if="userStore.member.id === hotplace.userId"
                    >
                      수정
                    </button>
                    <button
                      type="button"
                      class="btn btn-outline-danger rounded-pill px-4"
                      @click="onDeleteArticle"
                      v-if="userStore.member.id === hotplace.userId"
                    >
                      삭제
                    </button>
                    <button
                      type="button"
                      class="btn btn-outline-success rounded-pill px-4"
                      @click="moveList"
                    >
                      목록으로 이동...
                    </button>
                  </div>
                </form>
              </div>
              <img :src="ImgPath" :alt="ImgPath" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
