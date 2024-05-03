<script>
import childComponent from './components/newComponent.vue'

export default {
  // 자식 컴포넌트를 등록
  components: {
    childComponent
  }
}
</script>

<template>
  <childComponent/>
</template>