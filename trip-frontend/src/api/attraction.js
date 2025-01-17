import { localAxios } from "./http-commons.js";
const axios = localAxios();

const BASE_URL = "/attractionapi";

export const getAttractions = async (queryString) => {
  try {
    const res = await axios.get(`${BASE_URL}/list?${queryString}`);
    return res.data;
  } catch (error) {
    // console.log(error);
    return [];
  }
};

// contentId 해당하는 관광지 정보를 반환하는 함수입니다.
export const getAttraction = async (contentId, success, fail) => {
  await axios.get(`${BASE_URL}/${contentId}`).then(success).catch(fail);
};

// user 해당하는 추천 관광지 정보를 반환하는 함수입니다.
export const suggestAttraction = async (user, success, fail) => {
  await axios
    .post(`${BASE_URL}/suggest`, JSON.stringify(user))
    .then(success)
    .catch(fail);
};

export const getTripAttraction = async (contentId) => {
  try {
    const res = await axios.get(`${BASE_URL}/${contentId}`);
    return res.data;
  } catch (error) {
    console.log(error);
    return {};
  }
};

// 인기 관광지 정보를 반환
export const getTopAttractions = async (success, fail) => {
  await axios.get(`${BASE_URL}/list/top`).then(success).catch(fail);
};
