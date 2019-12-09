const gulp = require('gulp');
const sass = require('gulp-sass');
const concat = require('gulp-concat');
const browserfy = require('gulp-browserify');

gulp.task('materialize:css', function(){
    return gulp.src('./scss/materialize/materialize.scss')
        .pipe(concat('materialize.scss'))
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('css'));
});

gulp.task('materialize:js', function(){
    return gulp.src('.js/materialize/*.js')
        .pipe(concat('materialize.js'))
        .pipe(gulp.dest('./js/bin'))
});

gulp.task('sass', function() {
    return gulp.src('./scss/*.scss')
        .pipe(concat('main.scss'))
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('css'));
});

gulp.task('sass:watch', function() {
    gulp.watch('./scss/*.scss', ['sass']);
});

gulp.task('default', ['sass', 'sass:watch']);