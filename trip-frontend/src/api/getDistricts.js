import { localAxios } from "@/api/http-commons";
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