<script>
import childComponent from './ChildComponent.vue'

export default {
  // 자식 컴포넌트를 등록
  components: {
    childComponent
  },
  data() {
    return {
      greeting: 'Parent 의 데이터이다!!'
    }
  }
}
</script>

<template>
  <childComponent :message-from-parent="greeting"/>
</template>