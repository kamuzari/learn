<script>
import ChildComp from './ChildComponent.vue'

export default {
  components: {
    ChildComp
  },
  data() {
    return {
      childMsg: '자식 컴포넌트로부터 아직 메시지를 받지 못했어요!'
    }
  }
}
</script>

<template>
  <ChildComp  @response="(msg)=> childMsg=msg"/>
  <p>{{ childMsg }}</p>
</template>