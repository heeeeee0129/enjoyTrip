<script setup>
import { ref, onMounted } from "vue";
import { getAttraction } from "@/api/attraction";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

const props = defineProps({ location: Object });
const attraction = ref({});
const router = useRouter();

onMounted(() => {
  AOS.init();
  getAttract(props.location.contentId);
});

const getAttract = async (contentId) => {
  const success = (response) => {
    attraction.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getAttraction(contentId, success, fail);
};

const goDetail = () => {
  router.push({
    name: "TripDetail",
    params: { contentId: props.location.contentId },
  });
};
</script>

<template>
  <div
    class="p-3 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"
    @click="goDetail"
    data-aos="fade-up"
    data-aos-duration="1000">
    <div class="rounded-lg overflow-hidden">
      <div class="relative">
        <img
          :src="attraction.firstImage"
          alt="location.title"
          class="w-full h-64 object-cover" />
        <div class="absolute bottom-0 left-0 p-4">
          <h3 class="text-xl font-semibold text-white">
            {{ attraction.title }}
          </h3>
        </div>
      </div>
    </div>
    <div class="p-4 h-[300px] overflow-auto custom-scrollbar">
      <p class="text-gray-700 mb-4">{{ location.memo }}</p>
    </div>
  </div>
</template>

<style scoped>
.custom-scrollbar {
  scrollbar-width: thin;
  scrollbar-color: #a0aec0 #edf2f7;
}

.custom-scrollbar::-webkit-scrollbar {
  width: 8px;
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: #edf2f7;
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: #a0aec0;
  border-radius: 10px;
  border: 2px solid #edf2f7;
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background-color: #718096;
}

.custom-scrollbar::-webkit-scrollbar-thumb:active {
  background-color: #4a5568;
}

.custom-scrollbar::-webkit-scrollbar-corner {
  background: #edf2f7;
}
</style>
