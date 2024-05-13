<template>
  <div>
    <!-- 댓글 목록 표시 -->
    <div class="mt-3">
      <h3 class="text-secondary">댓글</h3>
      <div v-if="comments.length === 0" class="text-secondary">댓글이 없습니다.</div>
      <div v-else>
        <div v-for="comment in comments" :key="comment.id" class="bg-light rounded p-2 my-2">
          <div class="fw-bold">{{ comment.userName }}</div>
          <div>{{ comment.content }}</div>
          <div class="text-secondary">{{ comment.created_at }}</div>
        </div>
      </div>
    </div>

    <!-- 댓글 작성 폼 -->
    <div class="mt-3">
      <textarea
        v-model="newComment"
        class="form-control rounded"
        rows="3"
        placeholder="댓글을 입력하세요..."
      ></textarea>
      <div class="d-flex justify-content-end mt-2">
        <button type="button" class="btn btn-primary rounded-pill" @click="addComment">
          댓글 작성
        </button>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style scoped></style>
