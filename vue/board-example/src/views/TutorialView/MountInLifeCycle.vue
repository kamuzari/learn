<script setup>
import { ref,onMounted } from 'vue'

const p = ref(null)
onMounted(()=>{
  p.value.textContent="마운트 완료!"
})

</script>

<template>
  <p ref="p">안녕</p>
</template>