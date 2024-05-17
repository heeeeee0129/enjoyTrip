import { localAxios } from "./http-commons";
const axios = localAxios();

const BASE_URL = "/favoriteapi";

// 즐겨찾기 개수 가져오기
export const countFavorite = async (hotNo, success, fail) => {
  await axios.get(`${BASE_URL}/favorite/count/${hotNo}`).then(success).catch(fail);
};

// 즐겨찾기 정보 저장
export const writeFavorite = async (favorite, success, fail) => {
  await axios.post(`${BASE_URL}/favorite`, JSON.stringify(favorite)).then(success).catch(fail);
};

// 즐겨찾기 정보 체크
export const checkFavorite = async (favorite, success, fail) => {
  await axios
    .post(`${BASE_URL}/favorite/check`, JSON.stringify(favorite))
    .then(success)
    .catch(fail);
};

// 해당 유저의 즐겨찾기 정보 목록 가져오기
export const getFavorites = async (userId, success, fail) => {
  await axios.get(`${BASE_URL}/favorite/${userId}`).then(success).catch(fail);
};

// 해당 유저의 해당 즐겨찾기 정보 삭제하기
export const deleteFavorite = async (favorite, success, fail) => {
  await axios
    .post(`${BASE_URL}/favorite/delete`, JSON.stringify(favorite))
    .then(success)
    .catch(fail);
};
