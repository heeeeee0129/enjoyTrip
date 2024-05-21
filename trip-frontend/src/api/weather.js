import { localAxios } from "./http-commons";

const axios = localAxios();

// API 주소를 저장할 변수를 정의합니다.
const BASE_URL = "/weatherapi";

// 해당 지역 날씨 정보 반환
export const getWeather = async (param, success, fail) => {
  await axios
    .post(`${BASE_URL}/weather`, JSON.stringify(param))
    .then(success)
    .catch(fail);
};
