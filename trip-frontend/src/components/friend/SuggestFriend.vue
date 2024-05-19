<script setup>
import SuggestFriendItem from "@/components/friend/Item/SuggestFriendItem.vue";
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <!-- 제목 -->
          <h2 class="text-center py-3">추천 친구</h2>

          <!-- 글목록 테이블 -->
          <div class="table-responsive">
            <table class="table table-hover">
              <thead class="text-center">
                <tr>
                  <th scope="col">ID</th>
                  <th scope="col">이름</th>
                  <th scope="col">Email</th>
                  <th scope="col">주소</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <SuggestFriendItem />
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
