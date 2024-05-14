<script setup>
import NoticeFormItem from "@/components/notice/item/NoticeFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="card my-3 shadow-sm">
          <div class="card-body">
            <h2 class="card-title py-3 text-center">공지사항 수정</h2>
          </div>
          <NoticeFormItem type="modify" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
