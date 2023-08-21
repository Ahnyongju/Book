import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private List<Review> reviews = new ArrayList<>();
    private List<Rating> ratings = new ArrayList<>();

    // 생성자
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    // ISBN 반환
    public String getIsbn() {
        return isbn;
    }

    // 리뷰 추가
    public void addReview(Review review) {
        reviews.add(review);
    }

    // 평점 추가
    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    // 평균 평점 계산
    public double averageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getValue();
        }
        return sum / ratings.size();
    }

    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ")";
    }
}

class Publisher {
    private String name;
    private String address;

    public Publisher(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return name + " (" + address + ")";
    }
}

class Review {
    private String reviewer;
    private String comment;

    public Review(String reviewer, String comment) {
        this.reviewer = reviewer;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return reviewer + ": " + comment;
    }
}

class Rating {
    private double value;
    private String reviewer;

    public Rating(double value, String reviewer) {
        this.value = value;
        this.reviewer = reviewer;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return reviewer + " rated " + value + "/5";
    }
}

// 도서관 클래스
class Library {
    private List<Book> books = new ArrayList<>();
    private List<Publisher> publishers = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public Book searchByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return books;
    }
}

public class BookManagementSystem {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== 도서 관리 시스템 =====");
            System.out.println("1. 도서 추가");
            System.out.println("2. 도서 검색");
            System.out.println("3. 모든 도서 목록 출력");
            System.out.println("4. 도서 삭제");
            System.out.println("5. 출판사 관리");
            System.out.println("6. 도서 리뷰 추가");
            System.out.println("7. 도서 평점 추가");
            System.out.println("8. 최고 평점 도서 조회");
            System.out.println("9. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // 줄바꿈 처리

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchForBook();
                    break;
                case 3:
                    listAllBooks();
                    break;
                case 4:
                    removeBook();
                    break;
                case 5:
                    managePublishers();
                    break;
                case 6:
                    addReviewToBook();
                    break;
                case 7:
                    addRatingToBook();
                    break;
                case 8:
                    viewTopRatedBooks();
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("올바르지 않은 선택입니다!");
            }
        }
    }

    private static void addBook() {
        System.out.print("제목 입력: ");
        String title = scanner.nextLine();
        System.out.print("저자 입력: ");
        String author = scanner.nextLine();
        System.out.print("ISBN 입력: ");
        String isbn = scanner.nextLine();

        Book book = new Book(title, author, isbn);
        library.addBook(book);
        System.out.println("도서가 성공적으로 추가되었습니다!");
    }

    private static void searchForBook() {
        System.out.print("검색할 ISBN 입력: ");
        String isbn = scanner.nextLine();
        Book book = library.searchByIsbn(isbn);
        if (book == null) {
            System.out.println("도서를 찾을 수 없습니다!");
        } else {
            System.out.println(book);
        }
    }

    private static void listAllBooks() {
        List<Book> allBooks = library.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("등록된 도서가 없습니다!");
            return;
        }
        allBooks.forEach(System.out::println);
    }

    private static void removeBook() {
        System.out.print("삭제할 도서의 ISBN 입력: ");
        String isbn = scanner.nextLine();
        Book book = library.searchByIsbn(isbn);
        if (book == null) {
            System.out.println("도서를 찾을 수 없습니다!");
            return;
        }
        library.getAllBooks().remove(book);
        System.out.println("도서가 성공적으로 삭제되었습니다!");
    }

    private static void managePublishers() {
        System.out.println("1. 출판사 추가");
        System.out.println("2. 출판사 삭제");
        System.out.println("3. 모든 출판사 목록 출력");
        System.out.print("선택: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addPublisher();
                break;
            case 2:
                removePublisher();
                break;
            case 3:
                listAllPublishers();
                break;
            default:
                System.out.println("올바르지 않은 선택입니다!");
        }
    }

    private static void addReviewToBook() {
        System.out.print("리뷰를 추가할 도서의 ISBN 입력: ");
        String isbn = scanner.nextLine();
        Book book = library.searchByIsbn(isbn);
        if (book == null) {
            System.out.println("도서를 찾을 수 없습니다!");
            return;
        }
        System.out.print("리뷰어 이름 입력: ");
        String reviewer = scanner.nextLine();
        System.out.print("리뷰 내용 입력: ");
        String comment = scanner.nextLine();
        book.addReview(new Review(reviewer, comment));
        System.out.println("리뷰가 성공적으로 추가되었습니다!");
    }

    private static void addRatingToBook() {
        System.out.print("평점을 추가할 도서의 ISBN 입력: ");
        String isbn = scanner.nextLine();
        Book book = library.searchByIsbn(isbn);
        if (book == null) {
            System.out.println("도서를 찾을 수 없습니다!");
            return;
        }
        System.out.print("리뷰어 이름 입력: ");
        String reviewer = scanner.nextLine();
        System.out.print("평점 입력 (1~5 사이): ");
        double ratingValue = scanner.nextDouble();
        book.addRating(new Rating(ratingValue, reviewer));
        System.out.println("평점이 성공적으로 추가되었습니다!");
    }

    private static void viewTopRatedBooks() {
        Book topRatedBook = library.getTopRatedBook();
        if (topRatedBook == null) {
            System.out.println("등록된 도서가 없습니다!");
            return;
        }
        System.out.println("최고 평점의 도서: " + topRatedBook + " (평점: " + topRatedBook.averageRating() + ")");
    }
}
