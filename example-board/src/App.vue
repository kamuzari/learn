<script setup>
import { ref } from 'vue'
import ChildComp from './components/ChildComp.vue'

const childMsg = ref('자식 컴포넌트로부터 아직 메시지를 받지 못했어요!')
</script>

<template>
  <ChildComp @d="(msg)=>childMsg=msg"/>
  <p>{{ childMsg }}</p>
  <p> dasndjanjksd</p>
</template>