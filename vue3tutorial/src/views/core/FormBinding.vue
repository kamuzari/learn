<template>
  <h1> 폼 바인딩 페이지</h1>

  <h3> 기본 사용 예제</h3>
  <p> input 메시지 : {{ inputText }}</p>
  <input v-model="inputText" placeholder="메시지 입력하기" />

  <p> textarea 메시지 : {{ textarea }}</p>
  <textarea v-model="textarea" placeholder="메시지 입력하기" />

  <label for="checkbox">
    <p> checkbox(boolean 타입) 메시지 : {{ checkValue }}</p>
  </label>
  <input type="checkbox" id="checkbox" v-model="checkValue">

  <p> 배열 또는 set 자료구조에 여러개의 체크박스 값을 바인딩 할 수도 있어요!</p>
  <div> checkedName : {{ checkedNames }}</div>
  <div>
    <input type="checkbox" id="jack" value="잭" v-model="checkedNames">
    <label for="jack">쨲!</label>
  </div>
  <div>
    <input type="checkbox" id="john" value="존" v-model="checkedNames">
    <label for="john">쫀!</label>
  </div>
  <div>
    <input type="checkbox" id="bob" value="밥" v-model="checkedNames">
    <label for="bob">빱!</label>
  </div>
  <br>
  <br>
  <div>radio selectedValue => {{ radioSelectedValue }} </div>
  <input type="radio" id="one" value="하나" v-model="radioSelectedValue" />
  <label for="one">하나</label>

  <input type="radio" id="two" value="둘" v-model="radioSelectedValue" />
  <label for="two">둘</label>

  <br>
  <br>
  <div>selected 속성=> {{ selectedValue }}</div>

  <select v-model="selectedValue">
    <option value="" disabled> 다음중 하나를 선택해!</option>
    <option>가</option>
    <option>나</option>
    <option>다</option>
  </select>
  <br>
  <br>
  <div>v-for로 option value들 렌더링 할 수도 있어요! </div>

  <select>
    <option v-for="opt in selOpt" :value="opt.value">
      {{ opt.text }}
    </option>
  </select>
</template>

<script setup>
import { ref } from "vue";

const inputText = ref('');
const textarea = ref('');
const checkValue = ref(true)
const checkedNames = ref([])
const selectedValue = ref('')
const radioSelectedValue = ref()
const selValue = ref('1')
const selOpt = ref([
  { text: '하나', value: '1' },
  { text: '둘', value: '2' },
  { text: '셋', value: '3' },
])
</script>

<style scoped></style>