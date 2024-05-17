<script setup>
import NoticeFormItem from "@/components/notice/item/NoticeFormItem.vue";
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-10">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <h2 class="py-3 text-center">공지사항 작성</h2>
        </div>
        <NoticeFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
