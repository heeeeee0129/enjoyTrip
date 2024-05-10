import { localAxios } from "@/util/http-commons";

const axios = localAxios();

// API 주소를 저장할 변수를 정의합니다.
const BASE_URL = "/boardapi";

// 등록된 모든 게시판 글 정보를 반환하는 함수입니다.
export const listArticle = (param, success, fail) => {
  axios.get(`${BASE_URL}/board`, { params: param }).then(success).catch(fail);
};

// articleno에 해당하는 게시판 글 정보를 반환하는 함수입니다.
export const getArticle = (articleNo, success, fail) => {
  axios.get(`${BASE_URL}/board/${articleNo}`).then(success).catch(fail);
};

// 등록된 모든 게시판 글 정보를 정렬 기준에 따라 반환하는 함수입니다.
// export const sortArticle = (sortby, success, fail) => {
//   axios.get(`${BASE_URL}/board/sort/${sortby}`).then(success).catch(fail);
// };

// 게시판의 글을 저장하는 함수입니다.
export const writeArticle = (article, success, fail) => {
  return axios.post(`${BASE_URL}/board`, JSON.stringify(article)).then(success).catch(fail);
};

// 게시판의 글을 수정하는 함수입니다.
export const modifyArticle = (article, success, fail) => {
  return axios.put(`${BASE_URL}/board`, JSON.stringify(article)).then(success).catch(fail);
};

// 게시판의 글을 삭제하는 함수입니다.
export const deleteArticle = (articleno, success, fail) => {
  return axios.delete(`${BASE_URL}/board/${articleno}`).then(success).catch(fail);
};
