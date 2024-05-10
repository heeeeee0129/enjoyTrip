import { localAxios } from "@/util/http-commons";

const axios = localAxios();

// 아이디 중복체크
export const checkId = (id, success, fail) => {
  axios.get(`/user/${id}`).then(success).then(fail);
};

export const getUsers = (success, fail) => {
  console.log("getUsers");
  axios.get(`/user`).then(success).catch(fail);
};

export const postUser = (user, success, fail) => {
  axios.log("postUser", user);
  axios.post(`/user`, JSON.stringify(user)).then(success).catch(fail);
};
