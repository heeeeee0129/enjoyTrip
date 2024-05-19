<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">여행 경로</h2>
      </div>
      <button
        @click="goBack"
        class="mx-auto hover:shadow-lg bg-gray-500 hover:bg-gray-700 text-white py-2 px-4 rounded"
      >
        뒤로 가기
      </button>
      <hr class="border-gray-400" />
    </div>
    <div id="map" class="mt-3 rounded shadow-sm p-10" style="width: 100%; height: 550px"></div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="location in trip.tripLocations" :key="location.id">
        <TripPlanDetailItem :location="location" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import Swal from "sweetalert2";
import { getTripPlan } from "@/api/tripplan";
import TripPlanDetailItem from "@/components/trip/item/TripPlanDetailItem.vue";
import {loadKakaoMapScript, getCarDirection} from "@/utils/load-map"

const route = useRoute();
const tripId = Number(route.params.id);
let map = null;
const trip = ref({});
onMounted(async() => {
  await getTrip();
  await loadKakaoMapScript(map);
  map = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(37.500613, 127.036431),
    level: 7,
  });
  const data = await getCarDirection(trip.value.tripLocations);
  const linePath = [];
  console.log(data);
  console.log("요금 정보" , data.routes[0].summary.fare);
  console.log("전체 거리", data.routes[0].summary.distance);
  console.log("전체 소요시간", data.routes[0].summary.time);
  data.routes[0].sections[0].roads.forEach(road => {
    const vertex = road.vertexes;
    linePath.push(new window.kakao.maps.LatLng(vertex[1], vertex[0]));
    linePath.push(new window.kakao.maps.LatLng(vertex[3], vertex[2]));
  })
  
  var polyline = new window.kakao.maps.Polyline({
    path: linePath,
    strokeWeight: 5,
    strokeColor: 'red',
    strokeOpacity: 0.7,
    strokeStyle: 'solid'
  }); 
  polyline.setMap(map);
  map.setCenter(new window.kakao.maps.LatLng(trip.value.tripLocations[0].lat, trip.value.tripLocations[0].lng));
  
});

const getTrip = async () => {
  const success = (response) => {
    trip.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "Error!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getTripPlan(tripId, success, fail).then(() => getCarDirection(trip.value.tripLocations));
  
};

const goBack = () => {
  window.history.back();
};
</script>

<style scoped>
#detail-header {
  margin-bottom: 2rem;
}

/* 이미지 위에 텍스트 오버레이 스타일 */
.relative {
  position: relative;
}

.absolute {
  position: absolute;
}

.bottom-0 {
  bottom: 0;
}

.left-0 {
  left: 0;
}

.p-4 {
  padding: 1rem;
}

.text-white {
  color: #ffffff;
}
button:hover {
  transform: translateY(-2px);
}
</style>
