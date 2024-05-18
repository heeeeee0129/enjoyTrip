import { localAxios } from "./http-commons";

const axios = localAxios();
const BASE_URL = "/friendapi";

// 친구 목록을 가져옴
export const listFriends = async (userId, success, fail) => {
  await axios.get(`${BASE_URL}/friend/${userId}`).then(success).catch(fail);
};

// 친구 추가
export const addFriend = async (friend, success, fail) => {
  await axios.post(`${BASE_URL}/friend`, JSON.stringify(friend)).then(success).catch(fail);
};

// 친구 삭제
export const deleteFriend = async (friend, success, fail) => {
  await axios.post(`${BASE_URL}/friend/delete`, JSON.stringify(friend)).then(success).catch(fail);
};
