import { localAxios } from "./http-commons";

const axios = localAxios();

// API 주소를 저장할 변수를 정의합니다.
const BASE_URL = "/newsapi";

// 크롤링 해온 뉴스 반환
export const listArticle = async (success, fail) => {
  await axios.get(`${BASE_URL}/news`).then(success).catch(fail);
};
