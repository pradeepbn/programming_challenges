program_CXX_SRC := ${wildcard *.cpp}
program_INCLUDE :=
program_CXX_OBJS += ${program_CXX_SRC:.cpp=.o}
program_NAME    := BinaryTree

CPPFLAGS += $(foreach includedir,$(program_INCLUDE),-I$(includedir))
print:
	@echo $(program_CXX_SRC)
all: $(program_NAME)

$(program_NAME): $(program_CXX_OBJS)
	$(LINK.cc) $(program_CXX_OBJS) -o $(program_NAME)
