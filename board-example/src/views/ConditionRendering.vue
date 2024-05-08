<script setup>
import {ref} from 'vue'

const flag = ref(true);
const on=ref('on');
const off=ref('off');
</script>

<template>
  <button v-if="flag" @click="toggle =>{ flag=!flag}"> not activate button</button>
  <h2 v-if="flag" :class="on"> 현재 flag가 활성화 되었어요!</h2>

  <button v-else-if="!flag" @click="toggle => { flag=!flag}"> activate button</button>
  <h2 v-if="!flag" :class="off"> 현재 flag가 비! 활성화 되었어요!</h2>
</template>

<style scoped>
.on{
  color: green;
}

.off{
  color: red;
}
</style>