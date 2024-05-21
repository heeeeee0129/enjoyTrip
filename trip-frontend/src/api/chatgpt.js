import { localAxios } from "./http-commons";

const axios = localAxios();

// API 주소를 저장할 변수를 정의합니다.
const BASE_URL = "/chatgptapi";

// 해당 지역 날씨 정보 반환
export const getChatResponse = async (chatGPT, success, fail) => {
  await axios.post(`${BASE_URL}/propmt`, JSON.stringify(chatGPT)).then(success).catch(fail);
};
