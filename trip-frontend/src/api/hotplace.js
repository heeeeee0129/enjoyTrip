import { localAxios, fileAxios } from "@/util/http-commons";

const axios = localAxios();
const faxios = fileAxios();
const BASE_URL = "/hotplaceapi";

// 핫플레이스 글 목록을 가져오는 함수
export const listArticles = async (success, fail) => {
  await axios.get(`${BASE_URL}/hotplace`).then(success).catch(fail);
};

// 특정 핫플레이스 글 정보를 가져오는 함수
export const getArticle = async (hotNo, success, fail) => {
  await axios.get(`${BASE_URL}/hotplace/${hotNo}`).then(success).catch(fail);
};

// 핫플레이스 글을 작성하는 함수
export const writeArticle = async (hotplace, success, fail) => {
  const formData = new FormData();
  const json = JSON.stringify(hotplace);
  const blob = new Blob([json], { type: "application/json" });
  formData.append("hotplace", blob);
  formData.append("file", hotplace.fileInfo);

  await faxios.post(`${BASE_URL}/hotplace`, formData).then(success).catch(fail);
};

// 핫플레이스 글을 수정하는 함수
export const modifyArticle = async (hotplace, success, fail) => {
  const formData = new FormData();
  const json = JSON.stringify(hotplace);
  const blob = new Blob([json], { type: "application/json" });
  formData.append("hotplace", blob);
  formData.append("file", hotplace.fileInfo);

  await faxios.put(`${BASE_URL}/hotplace`, formData).then(success).catch(fail);
};

// 핫플레이스 글을 삭제하는 함수
export const deleteArticle = async (hotNo, success, fail) => {
  await axios.delete(`${BASE_URL}/hotplace/${hotNo}`).then(success).catch(fail);
};
