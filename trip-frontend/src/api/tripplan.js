import { localAxios } from "./http-commons";

const axios = localAxios();
const BASE_URL = "/tripplanapi";

// 여행계획 글을 작성하는 함수
export const writeTripPlan = async (tripplan, success, fail) => {
  await axios.post(`${BASE_URL}/tripplan`, JSON.stringify(tripplan)).then(success).catch(fail);
};

export const listTripPlan = async (success, fail) => {
  await axios.get(`${BASE_URL}/tripplan`).then(success).catch(fail);
};

export const listUserTripPlan = async (userId, success, fail) => {
  await axios.get(`${BASE_URL}/tripplan/user/${userId}`).then(success).catch(fail);
};

export const getTripPlan = async (id, success, fail) => {
  await axios.get(`${BASE_URL}/tripplan/${id}`).then(success).catch(fail);
};

// 여행계획 글을 삭제하는 함수
export const deleteTripPlan = async (id, success, fail) => {
  await axios.delete(`${BASE_URL}/tripplan/${id}`).then(success).catch(fail);
};
