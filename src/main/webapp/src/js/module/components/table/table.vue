<template>
  <div class="v-table-wrap">
    <table class="v-table">
      <thead>
        <tr class="v-thead">
          <slot name="t-check"></slot>
          <slot name="d-header" v-for="(item, $index) in headerData" :data="item" :idx="$index"></slot>
          <slot name="t-header"></slot>
        </tr>
      </thead>

      <tbody class="v-tbody">
        <slot name="t-body" v-for="(item, $index) in itemsData" :data="item" :idx="$index"></slot>
      </tbody>
    </table>
  </div>
</template>

<script>
import sortBy from 'js/lib/util/sortBy'

export default {
  data () {
    return {
      sortName: '', // 排序字段
      sortAsc: '', // 正序倒序
      headerData: this.header, // 表头数据
      itemsData: this.items // 列表数据
    }
  },
  props: {
    header: {
      type: Array,
      default: []
    },
    items: {
      type: Array,
      default: []
    },
    nowrap: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    items: function (val) {
      this.itemsData = val
    },
    header: function (val) {
      this.headerData = val
    }
  },
  methods: {
    order () {
      let nodeAry = document.querySelectorAll('th p[sort-key]')
      if (nodeAry.length) {
        for (let i = 0, len = nodeAry.length; i < len; i++) {
          nodeAry[i].addEventListener('click', e => {
            let _name = nodeAry[i].getAttribute('sort-key')
            let _allSortDom = document.querySelectorAll('.sort')
            let _sortDom = nodeAry[i].querySelector('.sort')

            _allSortDom.forEach(function (e) {
              e.className = 'sort'
            })

            if (_name != this.sortName) {
              this.sortName = _name
              this.sortAsc = ''
            }

            this.sortAsc = this.sortAsc ? '' : 'asc'

            _sortDom.className = this.sortAsc ? 'sort up' : 'sort down'

            this.itemsData.sort(sortBy(this.sortName, this.sortAsc))
          }, false)
        }
      }
    },
    widthAuto () {
      let nodeAry = document.querySelectorAll('th')
      if (nodeAry.length && this.nowrap) {
        for (let i = 0, len = nodeAry.length; i < len; i++) {
          nodeAry[i].setAttribute('nowrap', 'nowrap')
        }
      }
    }
  },
  mounted: function () {
    this.order()
    this.widthAuto()
  }
}
</script>

<style lang="scss" scoped>
@import '../main';

.v-table-wrap {
  overflow: auto;
  .v-table {
    min-width: 100%;
    width: auto;
    background: $head-start-bgcolor;
    font-size: 14px;
    border: 1px solid $border-default-color;
    .v-thead {
      height: 40px;
      font-size: 15px;
      th {
        font-weight: normal;
        padding: 8px;
        user-select: none;
        text-align: center;
        color: #fff;
        &.left {
          text-align: left;
        }
        &.center {
          text-align: center;
        }
        &.right {
          text-align: right;
        }

        p {
          &[sort-key] {
            cursor: pointer;
          }
          .sort {
            margin-left: 3px;
            position: relative;
            i {
              font-size: 12px;
              position: absolute;
              left: 0;
              color: #fff;
              &.up {
                top: -5px;
              }
              &.down {
                top: 3px;
              }
            }

            &.up {
              i {
                &.up {
                  color: #1875D1;
                }
                &.down {
                  color: #fff;
                }
              }
            }

            &.down {
              i {
                &.up {
                  color: #fff;
                }
                &.down {
                  color: #1875D1;
                }
              }
            }
          }
        }
      }
    }
    .v-tbody {
      tr {
        height: 42px;
        background: #fff;
        border-top: solid #ecf3ff 1px;
        transition: background 0.5s;
        &:hover {
          background: #f6faff;
        }
        td {
          padding: 8px;
          text-align: center;
          &.left {
            text-align: left;
          }
          &.center {
            text-align: center;
          }
          &.right {
            text-align: right;
          }
        }
      }
    }
  }
}
</style>
