<script setup>
import HotPlaceFormItem from "@/components/hotplace/item/HotPlaceFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="card my-3 shadow-sm">
          <div class="card-body">
            <h2 class="card-title py-3 text-center">핫플레이스 수정</h2>
          </div>
          <HotPlaceFormItem type="modify" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
