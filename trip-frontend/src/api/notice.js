import { localAxios } from "@/util/http-commons";

const axios = localAxios();
const BASE_URL = "/noticeapi";

// 공지사항 목록을 가져오는 함수
export const listArticles = async (success, fail) => {
  await axios.get(`${BASE_URL}/notice`).then(success).catch(fail);
};

// 특정 공지사항 정보를 가져오는 함수
export const getArticle = async (articleNo, success, fail) => {
  await axios.get(`${BASE_URL}/notice/${articleNo}`).then(success).catch(fail);
};

// 공지사항을 작성하는 함수
export const writeArticle = async (notice, success, fail) => {
  await axios.post(`${BASE_URL}/notice`, JSON.stringify(notice)).then(success).catch(fail);
};

// 공지사항을 수정하는 함수
export const modifyArticle = async (notice, success, fail) => {
  await axios.put(`${BASE_URL}/notice`, JSON.stringify(notice)).then(success).catch(fail);
};

// 공지사항을 삭제하는 함수
export const deleteArticle = async (articleNo, success, fail) => {
  await axios.delete(`${BASE_URL}/notice/${articleNo}`).then(success).catch(fail);
};
