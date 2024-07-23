<script setup>
import {ref} from 'vue';

const count = ref(0);
function increment() {
  count.value++;
}

</script>

<template>
  <button @click="increment"> @click=${함수이름} - 숫자 세기 : {{ count }}</button>
  <br>
  <br>
  <button v-on:click="increment"> v-on:click=${함수이름} - 숫자 세기 : {{ count }}</button>
</template>