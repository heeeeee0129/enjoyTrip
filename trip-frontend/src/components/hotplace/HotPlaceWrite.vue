<script setup>
import HotPlaceFormItem from "@/components/hotplace/item/HotPlaceFormItem.vue";
</script>

<template>
  <div class="p-10 mt-6">
    <div class="row px-10">
      <div class="mt-3 text-center fw-bolder">
        <h2 class="">나의 여행지 공유하기</h2>
      </div>
    </div>
    <div class="px-32">
      <hr class="border-gray-600 border-1 px-3 my-10" />
      <HotPlaceFormItem type="regist" />
    </div>
  </div>
</template>
<!-- 여기까지 -->
<!-- <div class="row justify-content-center">
    <div class="col-lg-10">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <h2 class="py-3 text-center">나의 여행지 공유하기</h2>
        </div>
        <HotPlaceFormItem type="regist" />
      </div>
    </div>
  </div>
</template> -->

<style scoped></style>
