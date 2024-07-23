<script setup>
import { ref } from 'vue'
import ChildComp from '@/components/dynimic/emits/ChildComponent.vue'

const childMsg = ref('No child msg yet')
</script>

<template>
  <ChildComp @response="(normalValue) => childMsg = normalValue" />
  <p>{{ childMsg }}</p>
</template>