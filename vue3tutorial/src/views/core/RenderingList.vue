<script setup>
import {fakerKO as faker} from "@faker-js/faker";
import {reactive, ref} from "vue";

const items = ref(Array.from({length: 10}).map(v => {
  return {message: faker.commerce.product()};
}));

const product = reactive({
  productName: faker.commerce.productName(),
  price: faker.commerce.price(),
  isbn: faker.commerce.isbn()
});
</script>

<template>
  <h1>렌더 리스트뷰!</h1>
  <div>일반 순회</div>
  <li v-for="item in items">
    {{ item.message }}
  </li>

  <div> index 사용</div>
  <li v-for="(item, index) in items">
    {{ index }} , {{ item.message }}
  </li>

  <div> key 사용</div>
  <li v-for='(value, key) in product'> <!--@review value, key 는 순서에 의존한다, 순서가 바뀌면 의도대로 나오지 않는다.-->
    key => {{ key }} , value => {{ value }}
  </li>

</template>

<style scoped>

</style>