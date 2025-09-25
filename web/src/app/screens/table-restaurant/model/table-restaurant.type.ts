export interface TableRestaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true)
  @NotBlank(message = Messages.EMPTY_NUMBER)
  @Length(max=10, message = Messages.SIZE_10)
  private String number;

  @Column
  @Enumerated(EnumType.ORDINAL)
  private TableStatus status;

  @Column
  @NotNull(message = Messages.EMPTY_PLACE)
  @Max(value = 10, message = Messages.SIZE_10)
  private Long places;
}
