<script setup>
import {computed, ref} from 'vue'
import ChildComp from '@/components/dynimic/props/ChildComponent.vue'

const greeting = computed(() => {
  return toMessage.value;
})

const toMessage = ref()
const flag = ref(true);
const go = () => {
  if (!flag.value) {
    toMessage.value = 'flag === false'
  } else {
    toMessage.value = 'flag === true'
  }
  flag.value = !flag.value

}

</script>

<template>
  <button @click=go>
    go!
  </button>
  <ChildComp :msg="greeting"/>
</template>