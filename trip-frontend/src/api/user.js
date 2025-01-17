import { localAxios } from "./http-commons";

const axios = localAxios();

// 회원 정보 조회
export const getUser = async (id, success, fail) => {
  await axios.get(`/userapi/user/checkId/${id}`).then(success).catch(fail);
};

// 전체 회원 정보 조회
export const getUserList = async (success, fail) => {
  await axios.get(`/userapi/user/list`).then(success).catch(fail);
};

// 추천 친구 정보 조회
export const getSuggestList = async (userId, success, fail) => {
  await axios.get(`/userapi/user/suggest/${userId}`).then(success).catch(fail);
};

// 비밀번호 찾기
export const findPass = (id, success, fail) => {
  axios
    .get("/userapi/user/findPass", {
      params: {
        id,
      },
    })
    .then(success)
    .catch(fail);
};

// 회원 가입
export const join = (member, success, fail) => {
  axios.post("/userapi/user", JSON.stringify(member)).then(success).catch(fail);
};

//로그인;
export const login = (member, success, fail) => {
  axios
    .post("/userapi/user/login", JSON.stringify(member))
    .then(success)
    .catch(fail);
};

// 로그아웃
export const logout = (success, fail) => {
  axios.post("/userapi/user/logout").then(success).catch(fail);
};

// 회원 정보 수정
export const modify = (member, success, fail) => {
  axios.put("/userapi/user", JSON.stringify(member)).then(success).catch(fail);
};

// 회원 탈퇴
export const remove = (id, success, fail) => {
  axios
    .delete("/userapi/user/remove", {
      params: {
        id,
      },
    })
    .then(success)
    .catch(fail);
};

// 시도 정보 가져오기
export const getSidos = (success, fail) => {
  axios.get("/userapi/user/sido").then(success).catch(fail);
};

export const getGuguns = (sido, success, fail) => {
  axios.get(`/districtapi/gugun/${sido}`).then(success).catch(fail);
};
