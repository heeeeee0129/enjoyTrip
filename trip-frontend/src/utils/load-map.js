const { VITE_KAKAO_API_KEY,VITE_KAKAO_REST_API_KEY } = import.meta.env;
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

export async function getCarDirection(locations) {
  console.log(locations);
  const url = 'https://apis-navi.kakaomobility.com/v1/waypoints/directions';
  // 출발지와 도착지 설정
  const origin = {
    x: locations[0].lng,
    y: locations[0].lat
  };
  const destination = {
      x: locations[locations.length - 1].lng,
      y: locations[locations.length - 1].lat
  };

  // 중간 경유지 설정
  const waypoints = locations.slice(1, locations.length - 1).map((location, index) => ({
      name: `name${index}`,
      x: location.lng,
      y: location.lat
  }));

  const requestBody = {
    origin: origin,
    destination: destination,
    waypoints: waypoints,
    priority: "RECOMMEND",
    car_fuel: "GASOLINE",
    car_hipass: false,
    alternatives: false,
    road_details: false
  };

  // 요청 헤더 구성
  const headers = {
    "Content-Type": "application/json",
    "Authorization": `KakaoAK ${VITE_KAKAO_REST_API_KEY}`
  };
  try {
    const response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(requestBody)
    });

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.error('Error:', error);
  }

}
