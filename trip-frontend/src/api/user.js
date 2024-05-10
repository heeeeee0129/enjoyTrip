import { localAxios } from "@/util/http-commons";

const axios = localAxios();

// 회원 정보 조회
export const getUser = (id, success, fail) => {
  axios.get(`/userapi/user/${id}`).then(success).then(fail);
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

// 로그인
// export const login = (member, success, fail) => {
//   axios
//     .post("/userapi/user/login", JSON.stringify(member))
//     .then(success)
//     .catch(fail);
// };

// // 로그아웃
// export const logout = (success, fail) => {
//   axios.post("/userapi/user/logout").then(success).catch(fail);
// };

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
