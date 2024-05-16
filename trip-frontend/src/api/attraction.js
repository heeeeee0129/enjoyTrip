import { localAxios } from "./http-commons";
const axios = localAxios();
export const getAttractions = async (queryString) => {
  try {
    const res = await axios.get(`attractionapi/list?${queryString}`);
    return res.data;
  } catch (error) {
    console.log(error);
    return [];
  }
};
