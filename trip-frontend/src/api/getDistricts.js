import { localAxios } from "./http-commons";
const axios = localAxios();

export const fetchSidos = async () => {
  try {
    const res = await axios.get("userapi/user/sido");
    return res.data;
  } catch (error) {
    console.log(error);
    return [];
  }
};

export const fetchGuguns = async (sido) => {
  try {
    const res = await axios.get(`districtapi/gugun/${sido}`);
    return res.data;
  } catch (error) {
    console.log(error);
    return [];
  }
};

// 시 이름 가져오기
export const getSidoName = async (sidoCode) => {
  try {
    const res = await axios.get(`districtapi/sido/name/${sidoCode}`);
    return res.data;
  } catch (error) {
    console.log(error);
    return "";
  }
};

// 구 이름 가져오기
export const getGugunName = async (address) => {
  try {
    const res = await axios.post(`districtapi/gugun/name`, address);
    return res.data;
  } catch (error) {
    console.log(error);
    return "";
  }
};
