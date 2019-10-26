var gulp = require('gulp');
var sass = require('gulp-sass');
var sourcemaps = require('gulp-sourcemaps');
var cleanCSS = require('gulp-clean-css');
var concat = require('gulp-concat');

gulp.task('sass', function() {
    return gulp.src('./css/*.scss')
        .pipe(concat('main.scss'))
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('css'));
});

gulp.task('sass:watch', function() {
    gulp.watch('./css/*.scss', ['sass']);
});

gulp.task('default', ['sass', 'sass:watch']);