<template>
  <div>
    <!-- 댓글 목록 표시 -->
    <div class="mt-3">
      <div class="bg-light rounded p-2 my-2 d-flex align-items-center">
        <div>
          <div class="fw-bold">사용자1</div>
          <div>댓글 내용1</div>
          <div class="text-secondary">2024-05-13 14:30:00</div>
        </div>
        <div class="ms-auto">
          <button class="btn btn-outline-primary btn-sm me-2">수정</button>
          <button class="btn btn-outline-danger btn-sm">삭제</button>
        </div>
      </div>
    </div>

    <!-- 댓글 작성 폼 -->
    <div class="mt-3">
      <textarea
        v-model="newComment"
        class="form-control rounded"
        rows="3"
        placeholder="댓글을 입력하세요..."></textarea>
      <div class="d-flex justify-content-end mt-2">
        <button
          type="button"
          class="btn btn-outline-primary me-1 rounded-pill"
          @click="addComment">
          작성
        </button>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style scoped></style>
