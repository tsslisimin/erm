<template>
  <div class="wrapper">
    <Location>公章上传</Location>
    <div class="con">
      <Card :padding="10">
        <div class="gongzhang">
          <p><span>公章上传</span><input type="file" id="file" style="line-height: 20px;" accept="*" name="file" @change="getFile()"></p>
          <div class="yulan">
            <p><img :src="'/erm/api/ermFile/img/' + photoUrl" alt=""></p>
          </div>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'

export default {
  name: 'gongzhang',
  data () {
    return {
      photoUrl: '',
      fileData: null
    }
  },
  components: { Location },
  methods: {
    // 获取学校字段
    getInfo () {
      io.get(api.school.info, (res) => {
        let data = res.data.ermSchool

        this.photoUrl = data.seal
      })
    },
    // 上传文件
    getFile () {
      let file = document.getElementById('file')

      this.fileData = new FormData()
      this.fileData.append('file', file.files[0])

      io.post(api.file.upload, this.fileData, (res) => {
        this.photoUrl = res.data

        io.post(api.school.edit, {
          seal: this.photoUrl
        }, (res) => {

        })
      }, (e) => {
        this.$Message.error('文件上传出错！')
      }, (e) => {
        this.$Message.error('文件上传出错！')
      })
    }
  },
  mounted () {
    this.getInfo()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .gongzhang {
      padding-top: 10px;
      span {
        font-size: 13px;
        margin-right: 10px;
      }
      .yulan {
        margin-top: 10px;
        img {
          border: 1px solid #ddd;
          width: 400px;
          height: 400px;
        }
      }
    }
  }
}
</style>
