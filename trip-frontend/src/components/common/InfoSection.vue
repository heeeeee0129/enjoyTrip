<template>
  <div class="mx-[7%] mt-24">
    <div
      data-aos="fade-right"
      data-aos-duration="1000"
      class="text-black text-2xl font-bold my-2 ml-12">
      {{ props.sectionTitle }}
    </div>

    <div
      data-aos="fade-up"
      data-aos-duration="1000"
      class="grid grid-cols-3 gap-4 p-4">
      <div
        v-for="(item, index) in props.info"
        :key="index"
        :data-aos-duration="item.duration"
        @mouseover="mouseIn(item)"
        @mouseleave="mouseOut(item)"
        @click="navigateTo(item.link)"
        class="p-3 h-60 rounded-2xl m-2 text-white grid-cols-3"
        :class="{
          ' bg-opacity-80 transition-all bg-gray-900 shadow-none duration-500':
            item.hover,
          ' bg-opacity-60 transition-all bg-gray-700 shadow-gray-700 shadow-lg duration-500':
            !item.hover,
        }">
        <div
          v-if="!item.hover"
          class="flex flex-col items-center justify-center p-4">
          <!-- 아이콘 표시 -->
          <h3 class="text-3xl font-extrabold text-black text-center">
            {{ item.title }}
          </h3>
          <img
            :src="item.img"
            class="m-2 w-20 h-20 items-center mb-2 opacity-70" />
        </div>
        <p v-else class="text-lg font-bold p-4">{{ item.content }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const props = defineProps({
  info: {
    type: Array,
    required: true,
  },
  sectionTitle: {
    type: String,
    required: true,
  },
});

const router = useRouter();

const mouseIn = (item) => {
  item.hover = true;
};
const mouseOut = (item) => {
  item.hover = false;
};

const navigateTo = (link) => {
  router.push({ name: link });
};
</script>

<style scoped>
/* 아이콘을 중앙에 배치 */
.grid-cols-3 > div > div {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
