<script setup>
import {ref} from 'vue'
import ChildComp from './ChildComp.vue'

const greeting = ref('부모 컴포넌트로부터 💌을 전달받았어요!')
const greeting2 = ref('부모 컴포넌트로부터 💌을 전달받았어요!2')
const info = [{
  id: 1111,
  name: 'Dockerizer1',
  age: 1784
}, {
  id: 2222,
  name: 'Dockerizer2',
  age: 1784
}, {
  id: 33333,
  name: 'Dockerizer3',
  age: 1784
}]

</script>

<template>
  <ChildComp :msg="greeting" :msg2="greeting2" :info="info"/>
</template>