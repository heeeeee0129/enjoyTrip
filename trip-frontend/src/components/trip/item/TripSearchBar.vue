<template>
  <div>
    <form class="d-flex my-3 gap-x-6" role="search">
      <select
        id="sido"
        v-model="selectedSidoCode"
        class="text-sm sm:text-base placeholder-gray-500 select-text px-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400">
        <option value="0" disabled selected>시/도 선택</option>
        <option v-for="sido in sidos" :key="sido.code" :value="sido.code">
          {{ sido.name }}
        </option>
      </select>
      <select
        id="gugun"
        v-model="selectedGugunCode"
        class="text-sm sm:text-base placeholder-gray-500 px-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400">
        <option value="0" disabled selected>구/군 선택</option>
        <option v-for="gugun in guguns" :key="gugun.code" :value="gugun.code">
          {{ gugun.name }}
        </option>
      </select>

      <input
        v-model="searchKeyword"
        id="search-keyword"
        class="form-control me-2 w-full"
        placeholder="검색어"
        aria-label="검색어" />
      <button
        @click.prevent="searchTrips"
        id="btn-search"
        class="btn btn-outline-success"
        type="button">
        검색
      </button>
    </form>
    <div>
      <div class="text-center" data-aos="fade-up">
        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="select-all"
            @change="selectAll($event.target)" />
          <label class="form-check-label" for="select-all">모두</label>
        </div>
        <div
          class="form-check form-check-inline"
          v-for="type in tourismTypes"
          :key="type.value">
          <input
            class="form-check-input"
            type="checkbox"
            :id="`tourism-type-${type.value}`"
            name="tourism-type"
            :value="type.value" />
          <label class="form-check-label" :for="`tourism-type-${type.value}`">{{
            type.label
          }}</label>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { ref, watch, onMounted } from "vue";
import { fetchSidos, fetchGuguns } from "@/api/getDistricts";

export default {
  name: "searchBar",
  emits: ["search"], // define emits here
  setup(_, { emit }) {
    const sidos = ref([]);
    const guguns = ref([]);
    const selectedSidoCode = ref(0);
    const selectedGugunCode = ref(0);
    const searchKeyword = ref("");
    const tourismTypes = [
      { value: 12, label: "관광지" },
      { value: 14, label: "문화시설" },
      { value: 15, label: "축제공연행사" },
      { value: 25, label: "여행코스" },
      { value: 28, label: "레포츠" },
      { value: 32, label: "숙박" },
      { value: 38, label: "쇼핑" },
      { value: 39, label: "음식점" },
    ];

    onMounted(async () => {
      sidos.value = await fetchSidos();
    });

    watch(selectedSidoCode, async (newSidoCode) => {
      selectedGugunCode.value = 0;
      guguns.value = await fetchGuguns(newSidoCode);
    });

    const searchTrips = () => {
      const areaCode = selectedSidoCode.value || 0;
      const sigunguCode = selectedGugunCode.value || 0;
      const contentType = Array.from(
        document.querySelectorAll('input[name="tourism-type"]:checked')
      ).map((input) => input.value);
      const keyword = encodeURIComponent(searchKeyword.value);
      let queryString = ``;

      queryString += `&sido=${areaCode}&gugun=${sigunguCode}`;

      if (keyword) {
        queryString += `&keyword=${keyword}`;
      }
      if (contentType && contentType.length > 0) {
        contentType.forEach((type) => {
          queryString += `&contentTypeIds=${type}`;
        });
      }
      emit("search", queryString);
    };

    const selectAll = (checkbox) => {
      const checkboxes = document.querySelectorAll(
        'input[name="tourism-type"]'
      );
      checkboxes.forEach((cb) => {
        cb.checked = checkbox.checked;
      });
    };

    return {
      sidos,
      guguns,
      selectedSidoCode,
      selectedGugunCode,
      searchKeyword,
      tourismTypes,
      searchTrips,
      selectAll,
    };
  },
};
</script>

<style scoped>
#search-header {
  margin: 40px 0;
}

.form-select {
  margin-right: 10px;
}
</style>
