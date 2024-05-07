<script setup>
const emit = defineEmits(['d'])

emit('d', '자식 컴포넌트로부터 🌷를 받았어요!')
</script>

<template>
  <h2>자식 컴포넌트</h2>
</template>