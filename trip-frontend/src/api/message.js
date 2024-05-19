import { localAxios } from "./http-commons";

const axios = localAxios();

// API 주소를 저장할 변수를 정의합니다.
const BASE_URL = "/messageapi";

// 해당 유저가 받은 모든 메시지를 가져오는 함수입니다.
export const getRecieveMessages = async (userId, success, fail) => {
  await axios.get(`${BASE_URL}/message/recieve/${userId}`).then(success).catch(fail);
};

// 해당 유저가 보낸 모든 메시지를 가져오는 함수입니다.
export const getSendMessages = async (userId, success, fail) => {
  await axios.get(`${BASE_URL}/message/send/${userId}`).then(success).catch(fail);
};

// 해당 메시지를 가져오는 함수입니다.
export const getMessage = async (messageNo, success, fail) => {
  await axios.get(`${BASE_URL}/message/${messageNo}`).then(success).catch(fail);
};

// 메시지를 작성하는 함수입니다.
export const writeMessage = async (message, success, fail) => {
  await axios.post(`${BASE_URL}/message`, JSON.stringify(message)).then(success).catch(fail);
};

// 메시지 읽음 처리
export const updateRead = async (message) => {
  try {
    await axios.put(`${BASE_URL}/message/update`, message);
  } catch (error) {
    console.log(error);
  }
};
