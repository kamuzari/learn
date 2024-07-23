<script setup>
import { reactive, ref, watch } from 'vue';

const question = ref('')
const answer = ref('질문에는 보통 물음표가 포함됩니다. ;-)')
const loading = ref(false)

// watch는 ref에 직접 작동합니다
watch(question, async (newQuestion, oldQuestion) => {
  if (newQuestion.includes('?')) {
    loading.value = true
    answer.value = '생각 중...'
    try {
      const res = await fetch('https://yesno.wtf/api')
      answer.value = (await res.json()).answer
    } catch (error) {
      answer.value = '오류! API에 도달할 수 없습니다. ' + error
    } finally {
      loading.value = false
    }
  }
})

const obj = reactive({ count: 0 })

watch(obj, (newValue, oldValue) => {
  // 중첩된 속성 변경 시 실행됩니다
  // 참고: `newValue`는 여기서 `oldValue`와 동일합니다.
  // 두 값 모두 동일한 객체를 가리키기 때문입니다!
  console.log('변경!');
  console.log('new -> ' + newValue.count);
  console.log('old -> ' + oldValue.count);
})

setTimeout(() => {
  obj.count++
}, 3000);

setTimeout(() => {
  obj.count--
}, 3000);

setTimeout(() => {
  obj.count++
}, 3000);

const eagerValue = ref(0);
watch(
  () => eagerValue,
  (newValue, oldValue) => {
    console.log('EAGER!');
    console.log('new -> ' + newValue);
    console.log('old -> ' + oldValue);
  },
  { immediate: true }
)
</script>

<template>
  <p>
    예/아니오 질문을 하세요:
    <input v-model="question" :disabled="loading" />
  </p>
  <p>{{ answer }}</p>

  <div>즉시 실행 Eager</div>
  <p>{{ eagerValue }}</p>
</template>

<style scoped></style>