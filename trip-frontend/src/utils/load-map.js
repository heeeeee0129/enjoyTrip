const { VITE_KAKAO_API_KEY } = import.meta.env;
export const loadKakaoMapScript = () => {
  return new Promise((resolve, reject) => {
    if (typeof window.kakao !== "undefined") {
      resolve();
      return;
    }

    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_API_KEY}&libraries=services,clusterer,drawing`;
    script.onload = () => {
      window.kakao.maps.load(() => {
        resolve();
      });
    };
    script.onerror = reject;
    document.head.appendChild(script);
  });
};
