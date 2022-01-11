const englishMonthList = [
  '一月',
  '二月',
  '三月',
  '四月',
  '五月',
  '六月',
  '七月',
  '八月',
  '九月',
  '十月',
  '十一月',
  '十二月'
];

const getNewDate =  (date) => {
 let year = date.getFullYear();
 let month = date.getMonth();
 let day = date.getDate();
 return {year, month, day};
}

const getDate = (year, month, day) => {
  return new Date(year, month, day);
}

const englishMonth = (month) => {
  let engMonth;

  englishMonthList.map(() => {
    engMonth = englishMonthList[month]
  });

  return engMonth
}

const formatDate = (date) => {
  date = Number(date);
  return date < 10? `0${date}`:date;
}
export {
  getNewDate,
  getDate,
  englishMonth,
  formatDate
}