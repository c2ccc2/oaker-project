<template>
  <div class="cc-calendar">
    <calendarHeader
      :headOptions="headOptions"
      @handlePrevMonth="handlePrevMonth"
      @handleNextMonth="handleNextMonth"
      @handleGoMyMonth="handleGoMyMonth"
      @handleToday="handleToday"
    />
    <ul class="calendar-week">
      <li
        v-for="(item, index) in calendarTitleArr"
        :key="index"
        class="week-item"
      >
        {{ item }}
      </li>
    </ul>
    <ul class="calendar-view" v-if="headOptions.showcontent == 1">
      <li
        v-for="(item, index) in visibleCalendar"
        :key="index"
        :disabled="!isCurrentMonth(item.date)"
        class="date-view"
        :class="[
          { 'month-class': !isCurrentMonth(item.date) },
          { todayBg: isCurrentDay(item.date) },
          { handleDay: item.clickDay }
        ]"
        @click="handleClickDay(item)"
      >
        <span
          class="date-day"
          :style="dayStyle"
          :class="[{ 'opacity-class': !isCurrentMonth(item.date) }]"
        >
          <span style=" opacity: 0;">{{ item.day }}</span>
          <span>{{ item.day }}</span>
          <span
            v-if="item.week == '6' || item.week == '0' || item.status == 4 ||item.mystatus==4"
            style="color:red"
            >休</span
          >
          <span v-else style="color:green">班</span>
        </span>
        <span
          class="calendar-num"
          :class="[{ 'content-class': !isCurrentMonth(item.date) }]"
          v-show="
            (item.week != '6' && item.week != '0' && item.showday) ||
              item.mystatus == 1
          "
        >
          <el-result v-if="item.mystatus == 1" title="已报" icon="success">
            <template slot="icon">
              <el-button circle type="success" icon="el-icon-check"></el-button>
            </template>
          </el-result>
          <el-result v-else-if="item.mystatus == 2" title="缺报" icon="error">
            <template slot="icon">
              <el-button circle type="danger" icon="el-icon-close"></el-button>
            </template>
          </el-result>
          <el-result
            v-else-if="item.mystatus == 3"
            title="无需填报"
            icon="info"
          >
            <template slot="icon">
              <el-button circle type="info" icon="el-icon-minus"></el-button>
            </template>
          </el-result>
          <el-result
            v-else-if="item.mystatus == 4"
            title="节假日"
            icon="warning"
          >
            <template slot="icon">
              <el-button
                circle
                type="warning"
                icon="el-icon-magic-stick"
              ></el-button>
            </template>
          </el-result>
          <el-result v-else-if="item.mystatus == 5" title="请假" icon="warning">
            <template slot="icon">
              <el-button
                circle
                type="warning"
                icon="el-icon-close-notification"
              ></el-button>
            </template>
          </el-result>
          <el-result v-else-if="item.mystatus == 6" title="调休" icon="warning">
            <template slot="icon">
              <el-button
                circle
                type="warning"
                icon="el-icon-refresh"
              ></el-button>
            </template>
          </el-result>
        </span>
      </li>
    </ul>
    <ul class="calendar-view" v-else-if="headOptions.showcontent == 2">
      <li
        v-for="(item, index) in visibleCalendar"
        :key="index"
        :disabled="!isCurrentMonth(item.date)"
        class="date-view"
        :class="[
          { 'month-class': !isCurrentMonth(item.date) },
          { todayBg: isCurrentDay(item.date) },
          { handleDay: item.clickDay }
        ]"
        @click="handleClickDay(item)"
      >
        <span
          class="date-day"
          :style="dayStyle"
          :class="[{ 'opacity-class': !isCurrentMonth(item.date) }]"
        >
          <span style=" opacity: 0;">{{ item.day }}</span>
          <span>{{ item.day }}</span>
          <span v-if="item.week == '6' || item.week == '0'" style="color:red"
            >休</span
          >
          <span v-else style="color:green">班</span>
        </span>
        <span
          class="calendar-num"
          :class="[{ 'content-class': !isCurrentMonth(item.date) }]"
          v-show="
            (item.week != '6' && item.week != '0' && item.showday) ||
              item.users > 0
          "
        >
          <span v-if="item.users == 0 && item.AllUseHour == 0"
            ><div class="countPeople">无</div></span
          >
          <span v-else>
            <div class="countPeople">{{ item.users + "人" }}</div>
            <div class="alluseHour">{{ item.AllUseHour + "小时" }}</div></span
          >
        </span>
      </li>
    </ul>
  </div>
</template>

<script>
import "@/assets/css/reset.min.css";
import calendarHeader from "./canlendar-head.vue";
import * as utils from "@/assets/js/utils.js";

export default {
  name: "cc-calendar",
  componentName: "cc-calendar",
  props: {
    options: Object
  },
  components: {
    calendarHeader
  },
  data() {
    let { year, month, day } = utils.getNewDate(
      this.options.monthDate == ""
        ? new Date()
        : new Date(this.options.monthDate)
    );
    return {
      headOptions: {
        showcontent: this.options.showcontent,
        type: this.options.type,
        style: this.options.headStyle,
        date: ""
      },
      calendarTitleArr: [
        "周一",
        "周二",
        "周三",
        "周四",
        "周五",
        "周六",
        "周日 "
      ],
      time: { year, month, day },
      calendarList: []
    };
  },
  computed: {
    dayStyle: function() {
      return {
        textAlign: this.options.viewStyle.day
      };
    },
    visibleCalendar() {
      let calendatArr = [];
      let { year, month, day } = utils.getNewDate(
        utils.getDate(this.time.year, this.time.month, 1)
      );

      let currentFirstDay = utils.getDate(year, month, 1);

      // 获取当前月第一天星期几
      let weekDay = currentFirstDay.getDay();
      if (weekDay == 0) {
        weekDay += 7;
      }
      let startTime = currentFirstDay - (weekDay - 1) * 24 * 60 * 60 * 1000;

      let monthDayNum;
      if (weekDay == 5 || weekDay == 6) {
        monthDayNum = 42;
      } else {
        monthDayNum = 35;
      }

      for (let i = 0; i < monthDayNum; i++) {
        calendatArr.push({
          date: new Date(startTime + i * 24 * 60 * 60 * 1000),
          year: year,
          month: month + 1,
          day: new Date(startTime + i * 24 * 60 * 60 * 1000).getDate(),
          clickDay: false,
          week: new Date(startTime + i * 24 * 60 * 60 * 1000).getDay(),
          mydate:
            year +
            "-" +
            (month + 1 < 10 ? "0" + (month + 1) : month + 1) +
            "-" +
            (new Date(startTime + i * 24 * 60 * 60 * 1000).getDate() < 10
              ? "0" + new Date(startTime + i * 24 * 60 * 60 * 1000).getDate()
              : new Date(startTime + i * 24 * 60 * 60 * 1000).getDate()),
          projectData: this.options.hourDetail.filter(
            item =>
              item.date || item.mydate==
              year +
                "-" +
                (month + 1) +
                "-" +
                (new Date(startTime + i * 24 * 60 * 60 * 1000).getDate() < 10
                  ? "0" +
                    new Date(startTime + i * 24 * 60 * 60 * 1000).getDate()
                  : new Date(startTime + i * 24 * 60 * 60 * 1000).getDate())
          ),

          showday:
            new Date(
              Date.parse(
                year +
                  "-" +
                  (month + 1) +
                  "-" +
                  (new Date(startTime + i * 24 * 60 * 60 * 1000).getDate() < 10
                    ? "0" +
                      new Date(startTime + i * 24 * 60 * 60 * 1000).getDate()
                    : new Date(startTime + i * 24 * 60 * 60 * 1000).getDate())
              )
            ) < new Date()
        });
      }
      calendatArr.forEach(el => {
        el.AllUseHour = 0;
        el.users = 0;
        // if(el.status){
        //   el.mystatus=el.status
        // }else{
          el.mystatus=2
        // }
        el.projectData=el.projectData.filter(item=>item.date==el.mydate)
        // console.log("el", el);
        // el.status2=el.projectData[0].status
        el.projectData.forEach(dl => {
          // console.log('dl',dl.date==el.mydate)
          if (dl.hours) {
            el.AllUseHour += dl.hours;
          }
          if (dl.users) {
            el.users = dl.users.length;
          }
          if (dl.status) {
            el.mystatus = dl.status;
          }
        });
      });

      this.headOptions.date = ` ${year} ${utils.englishMonth(month)}`;
      // //console.log(utils.englishMonth(month));
      return calendatArr;
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      console.log("visibleCalendar", this.visibleCalendar);
      this.calendarList = this.visibleCalendar;
      this.calendarType = this.options.calendarType;
      // this.calendarType = this.options.type;
      //console.log("visibleCalendar", this.visibleCalendar);
      //console.log("calendarList", this.calendarList);
      //console.log("calendarType", this.calendarType);
      //console.log(111);
    },
    // 是否是当前月
    isCurrentMonth(date) {
      let { year: currentYear, month: currentMonth } = utils.getNewDate(
        utils.getDate(this.time.year, this.time.month, 1)
      );
      let { year, month } = utils.getNewDate(date);
      return currentYear == year && currentMonth == month;
    },
    // 是否是今天
    isCurrentDay(date) {
      let {
        year: currentYear,
        month: currentMonth,
        day: currentDay
      } = utils.getNewDate(new Date());
      let { year, month, day } = utils.getNewDate(date);
      return currentYear == year && currentMonth == month && currentDay == day;
    },
    // 跳转到某月
    handleGoMyMonth(dateTime) {
      //console.log("cli", dateTime);
      //console.log(111);
      this.time = {};
      let myMonth = dateTime.split("-");
      this.time.year = parseInt(myMonth[0]);
      this.time.month = parseInt(myMonth[1] - 1);
      this.time.day = parseInt(myMonth[2]);
      //console.log(this.time);
      // let prevMonth = utils.getDate(this.time.year, this.time.month, 1);
      // //console.log(prevMonth.getMonth()-1)
      // prevMonth.setMonth(prevMonth.getMonth() - 1);

      // //console.log(this.time)
      this.headOptions.date = `${utils.englishMonth(this.time.month)} ${
        this.time.year
      }`;
      this.$emit("handleGoMyMonth");
    },
    // 上一个月
    handlePrevMonth() {
      let prevMonth = utils.getDate(this.time.year, this.time.month, 1);
      // //console.log(prevMonth.getMonth()-1)
      prevMonth.setMonth(prevMonth.getMonth() - 1);
      this.time = utils.getNewDate(prevMonth);
      //console.log(this.time);
      this.headOptions.date = `${utils.englishMonth(this.time.month)} ${
        this.time.year
      }`;
      this.$emit("handlePrevMonth");
    },
    // 下一个月
    handleNextMonth() {
      let nextMonth = utils.getDate(this.time.year, this.time.month, 1);
      nextMonth.setMonth(nextMonth.getMonth() + 1);
      this.time = utils.getNewDate(nextMonth);
      this.headOptions.date = `${utils.englishMonth(this.time.month)} ${
        this.time.year
      }`;
      this.$emit("handleNextMonth");
    },
    // 点击回到今天
    handleToday() {
      this.time = utils.getNewDate(new Date());
      // this.returnDate();
      this.$emit("handleToday");
    },
    // 点击某一天
    handleClickDay(item) {
      //console.log(item);
      if(!this.isCurrentMonth(item.date)){
        return 0
      }
      this.$forceUpdate();
      this.$emit("handleClickDay", item);
      this.$set(item, "clickDay", !item.clickDay);
    },
    determineHolidays(month, date) {
      if (month == 1 && date == 1) return 4;
      if (month == 1 && date == 2) return 4;
      if (month == 1 && date == 3) return 4;
      if (month == 1 && date == 31) return 4;
      if (month == 2 && date == 1) return 4;
      if (month == 2 && date == 2) return 4;
      if (month == 2 && date == 3) return 4;
      if (month == 2 && date == 4) return 4;
      if (month == 2 && date == 5) return 4;
      if (month == 2 && date == 6) return 4;
      if (month == 4 && date == 3) return 4;
      if (month == 4 && date == 4) return 4;
      if (month == 4 && date == 5) return 4;
      if (month == 4 && date == 30) return 4;
      if (month == 5 && date == 1) return 4;
      if (month == 5 && date == 2) return 4;
      if (month == 5 && date == 3) return 4;
      if (month == 5 && date == 4) return 4;
      if (month == 6 && date == 3) return 4;
      if (month == 6 && date == 4) return 4;
      if (month == 6 && date == 5) return 4;
      if (month == 9 && date == 10) return 4;
      if (month == 9 && date == 11) return 4;
      if (month == 9 && date == 12) return 4;
      if (month == 10 && date == 1) return 4;
      if (month == 10 && date == 2) return 4;
      if (month == 10 && date == 3) return 4;
      if (month == 10 && date == 4) return 4;
      if (month == 10 && date == 5) return 4;
      if (month == 10 && date == 6) return 4;
      if (month == 10 && date == 7) return 4;
    }
  }
};
</script>

<style lang="scss">
.cc-calendar {
  padding: 23px 3px 3px;
  width: 100%;
  height: 100%;
  // background: #f9fafc;
  box-sizing: border-box;
  .calendar-week {
    display: flex;
    width: 100%;
    height: 46px;
    line-height: 46px;
    border: 1px solid #e4e7ea;
    border-right: none;
    .week-item {
      width: 14.285%;
      text-align: center;
      font-size: 14px;
      color: #424953;
      border-right: 1px solid #e4e7ea;
      font-weight: 600;
    }
  }
  .calendar-view {
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    border-left: 1px solid #e4e7ea;
    .date-view {
      width: 14.285%;
      height: 120px;
      // border: 1px solid transparent;
      border: 1px solid #e4e7ea;
      // border-right: 1px solid #e4e7ea;
      // border-bottom: 1px solid #e4e7ea;
      border-radius: 15px;
      box-sizing: border-box;
      cursor: pointer;
      &:hover {
        border: 1px solid #808488;
      }
      .date-day {
        padding: 8px 8px 0;
        // display: block;
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        font-size: 14px;
        color: #7f8794;
      }
      .calendar-num {
        margin-top: 6px;
        display: block;
        width: 100%;
        text-align: center;
        font-size: 30px;
        color: #424953;
      }
    }
    .opacity-class {
      opacity: 0.5;
    }
    .month-class {
      background-image: linear-gradient(
        45deg,
        rgba(000, 000, 000, 0.03) 25%,
        transparent 25%,
        transparent 50%,
        rgba(000, 000, 000, 0.03) 50%,
        rgba(000, 000, 000, 0.03) 75%,
        transparent 75%,
        transparent
      );
      background-size: 20px 20px;
      // opacity: 0;
    }
    .content-class {
      opacity: 0;
      // display: none;
    }
    .todayBg {
      border: 1px solid #2061ff;
    }
    .handleDay {
      // background: #2061ff !important;
      // .date-day {
      //   color: #bccfff !important;
      // }
      // .calendar-num {
      //   color: #fff !important;
      // }
    }
  }
}
.el-result {
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.countPeople,
.alluseHour {
  font-size: 18px;
  line-height: 30px;
  font-weight: 600;
}
.el-result__title {
  margin: auto;
  margin-top: 1rem;
  p {
    font-size: 12px !important;
  }
}
.el-result__icon {
  svg {
    width: 3rem;
    height: 3rem;
  }
}
</style>
