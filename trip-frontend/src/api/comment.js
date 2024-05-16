import { localAxios } from "./http-commons";

const axios = localAxios();

// API 주소를 저장할 변수를 정의합니다.
const BASE_URL = "/commentapi";

// 해당 게시글의 댓글 개수를 반환하는 함수입니다.
export const getCountComment = async (articleNo, success, fail) => {
  await axios
    .get(`${BASE_URL}/comment/count/${articleNo}`)
    .then(success)
    .catch(fail);
};

// 해당 게시글의 댓글 목록을 반환하는 함수입니다.
export const listComment = async (articleNo, success, fail) => {
  await axios
    .get(`${BASE_URL}/comment/list/${articleNo}`)
    .then(success)
    .catch(fail);
};

// 해당 댓글의 답글 목록을 반환하는 함수입니다.
export const listReComment = async (replyNo, success, fail) => {
  await axios
    .get(`${BASE_URL}/comment/listRe/${replyNo}`)
    .then(success)
    .catch(fail);
};

// 특정 댓글 정보를 반환하는 함수입니다.
export const getComment = async (replyNo, success, fail) => {
  await axios.get(`${BASE_URL}/comment/${replyNo}`).then(success).catch(fail);
};

// 댓글을 작성하는 함수입니다.
export const writeComment = async (comment, success, fail) => {
  await axios
    .post(`${BASE_URL}/comment`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
};

// 댓글을 수정하는 함수입니다.
export const modifyComment = async (comment, success, fail) => {
  await axios
    .put(`${BASE_URL}/comment`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
};

// 댓글을 삭제하는 함수입니다.
export const deleteComment = async (replyNo, success, fail) => {
  await axios
    .delete(`${BASE_URL}/comment/${replyNo}`)
    .then(success)
    .catch(fail);
};
