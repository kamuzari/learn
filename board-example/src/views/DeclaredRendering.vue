<script setup>
import {reactive, ref} from 'vue'

const message = ref('ref keyword!');
console.log(`변경 전@ -> ${message.value}`);
message.value = '메시지 변경!';
console.log(`변경 후! -> ${message.value}`);
const counter = reactive({
  count: 0
})
console.log(`counter 객체의 count 속성 값 -> ${counter.count} `);
counter.count++;
console.log(`counter 객체의 count 속성 값 변경! -> ${counter.count} `);

</script>

<template>
  <h1>{{ message }}</h1>
  <h1>거꾸로 말해요 -> {{ message }}</h1>
  <p> 숫자 세기 : {{ counter.count }}</p>
</template>