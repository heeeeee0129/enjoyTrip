<script setup>
import { ref, onMounted } from "vue";
import { getWeather } from "@/api/weather";

import Swal from "sweetalert2";

const info = ref({});
const props = defineProps({ weather: Object });
const emit = defineEmits(["changeModal"]);

onMounted(() => {
  getinfo();
});

const getinfo = async () => {
  const success = (response) => {
    info.value = response.data;
    // console.log(info.value);
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getWeather(props.weather, success, fail);
};
</script>

<template>
  <div class="weather-info">
    <div class="card">
      <div class="card-header text-center">
        <h2>오늘 날씨</h2>
      </div>
      <div class="card-body">
        <div v-if="info.currentTemp !== undefined" class="weather-item">
          <div class="icon">
            <img
              :src="'http://openweathermap.org/img/wn/' + info.icon + '.png'"
              alt="Weather Icon" />
          </div>
          <div class="description">
            <p class="state">{{ info.state }}</p>
            <p class="temp">현재 온도: {{ info.currentTemp.toFixed(1) }}°C</p>
            <p class="temp">최저 온도: {{ info.minTemp.toFixed(1) }}°C</p>
            <p class="temp">최고 온도: {{ info.maxTemp.toFixed(1) }}°C</p>
            <p class="humidity">습도: {{ info.humidity }}%</p>
            <p class="wind">풍속: {{ info.windSpeed }} m/s</p>
            <p class="sunrise">일출 시간: {{ info.sunrise }}</p>
            <p class="sunset">일몰 시간: {{ info.sunset }}</p>
          </div>
        </div>
        <div v-else>
          <p>날씨 정보를 불러오는 중입니다...</p>
        </div>
      </div>
      <div class="card-footer text-center">
        <button class="btn btn-outline-primary" @click="emit('changeModal')">
          닫기
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.weather-info {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.card {
  max-width: 400px;
  width: 100%;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-header {
  background-color: #007bff;
  color: #fff;
  padding: 10px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.card-body {
  padding: 20px;
}

.weather-item {
  display: flex;
  align-items: center;
}

.icon img {
  width: 50px;
}

.description {
  margin-left: 20px;
}

.state {
  font-size: 1.2rem;
  font-weight: bold;
}

.temp,
.humidity,
.wind,
.sunrise,
.sunset {
  font-size: 1rem;
  margin-top: 5px;
}
</style>
